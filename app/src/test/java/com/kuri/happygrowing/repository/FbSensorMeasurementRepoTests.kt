package com.kuri.happygrowing.repository

import android.os.Handler
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.kuri.happygrowing.shared.logging.ILogger
import com.kuri.happygrowing.shared.logging.getLogger
import com.kuri.happygrowing.stats.model.Measurement
import com.kuri.happygrowing.stats.model.SensorType
import com.kuri.happygrowing.stats.repository.measurement.FirestoreMeasurementRepository
import com.kuri.happygrowing.stats.repository.measurement.IMeasurementRepository
import com.kuri.happygrowing.stats.repository.measurement.OnRepositoryResult
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import java.lang.Exception
import java.lang.IllegalArgumentException


inline fun <reified T: Any> mock() = Mockito.mock(T::class.java)

@RunWith(MockitoJUnitRunner.Silent::class)
class FirestoreMeasurementRepoTests {

    @Mock
    private var measurmentColl: CollectionReference? = null

    @Mock
    private var lastMeasurementColl: CollectionReference? = null

    private var repo: IMeasurementRepository? = null

    private val logger = object: ILogger{
        override fun logError(msg: String) {
            println(msg)
        }

        override fun logDebug(msg: String) {
            println(msg)
        }

        override fun logInfo(msg: String) {
            println(msg)
        }
    }

    @Before
    fun initialize(){
        repo = FirestoreMeasurementRepository(measurmentColl!!, lastMeasurementColl!!, logger)
    }

    @Test
    fun getLastSuccessTest(){
        val tempDoc = getDocumentSnapshotForMeasurement("temperature", 10.0f, Date())
        val humDoc = getDocumentSnapshotForMeasurement("humidity", 10.0f, Date())

        val snapshot = Mockito.mock(QuerySnapshot::class.java)
        Mockito.`when`(snapshot.documents).thenReturn(listOf(tempDoc, humDoc))

        val snapshotTask: Task<QuerySnapshot> = mock()
        Mockito.`when`(snapshotTask.addOnSuccessListener(Mockito.any())).then {
            var res = it.arguments[0] as OnSuccessListener<QuerySnapshot>
            res.onSuccess(snapshot)
            mock<Task<QuerySnapshot>>()
        }
        Mockito.`when`(lastMeasurementColl!!.get()).thenReturn(snapshotTask)
        repo!!.getLastForAllSensors(object: OnRepositoryResult<Map<SensorType, Measurement>>{
            override fun onSuccessResult(result: Map<SensorType, Measurement>) {
                Assert.assertTrue(result.size == 2)
                Assert.assertTrue(result.containsKey(SensorType.TEMPERATURE))
                Assert.assertTrue(result.containsKey(SensorType.HUMIDITY))
            }

            override fun onError(e: Exception) {
                throw e
            }
        })
    }


    @Test
    fun getLastSuccess_SkipInvalidTest(){
        val tempDoc = getDocumentSnapshotForMeasurement("temperature", 10.0f, Date())
        val humDoc = getDocumentSnapshotForMeasurement("humidity", 10.0f, Date())
        val invalidDoc = getDocumentSnapshotForMeasurement("papas", 10f, Date())

        val snapshot = Mockito.mock(QuerySnapshot::class.java)
        Mockito.`when`(snapshot.documents).thenReturn(listOf(tempDoc, humDoc, invalidDoc))

        val snapshotTask: Task<QuerySnapshot> = mock()
        Mockito.`when`(snapshotTask.addOnSuccessListener(Mockito.any())).then {
            var res = it.arguments[0] as OnSuccessListener<QuerySnapshot>
            res.onSuccess(snapshot)
            mock<Task<QuerySnapshot>>()
        }
        Mockito.`when`(lastMeasurementColl!!.get()).thenReturn(snapshotTask)
        repo!!.getLastForAllSensors(object: OnRepositoryResult<Map<SensorType, Measurement>>{
            override fun onSuccessResult(result: Map<SensorType, Measurement>) {
                Assert.assertTrue(result.size == 2)
                Assert.assertTrue(result.containsKey(SensorType.TEMPERATURE))
                Assert.assertTrue(result.containsKey(SensorType.HUMIDITY))
            }

            override fun onError(e: Exception) {
                throw e
            }
        })
    }

    @Test
    fun getLastSuccess_Error(){
        val tempDoc = getDocumentSnapshotForMeasurement("temperature", 10.0f, Date())
        val humDoc = getDocumentSnapshotForMeasurement("humidity", 10.0f, Date())
        val invalidDoc = getDocumentSnapshotForMeasurement("papas", 10f, Date())

        val snapshot = Mockito.mock(QuerySnapshot::class.java)
        Mockito.`when`(snapshot.documents).thenReturn(listOf(tempDoc, humDoc, invalidDoc))

        val snapshotTask: Task<QuerySnapshot> = mock()
        Mockito.`when`(snapshotTask.addOnSuccessListener(Mockito.any())).then {
            val result = mock<Task<QuerySnapshot>>()
            Mockito.`when`(result.isSuccessful).thenReturn(false)
            Mockito.`when`(result.exception).thenReturn(NoSuchFieldException())
            result
        }
        Mockito.`when`(lastMeasurementColl!!.get()).thenReturn(snapshotTask)
        repo!!.getLastForAllSensors(object: OnRepositoryResult<Map<SensorType, Measurement>>{
            override fun onSuccessResult(result: Map<SensorType, Measurement>) {
                throw Exception()
            }

            override fun onError(e: Exception) {
                Assert.assertTrue(e is NoSuchFieldException)
            }
        })
    }

    private fun getDocumentSnapshotForMeasurement(type: String, value: Float, date: Date): DocumentSnapshot{
        var doc = Mockito.mock(DocumentSnapshot::class.java)
        try{
            val measurementResult = Measurement(value, date, type)
            Mockito.`when`(doc.toObject(Measurement::class.java)).thenReturn(measurementResult)
        } catch (e: IllegalArgumentException){
            Mockito.`when`(doc.toObject(Measurement::class.java)).thenThrow(IllegalArgumentException::class.java)
        }
        return doc
    }

}