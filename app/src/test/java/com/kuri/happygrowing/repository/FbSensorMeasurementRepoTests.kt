package com.kuri.happygrowing.repository

import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.kuri.happygrowing.shared.logging.ILogger
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
import kotlin.Exception
import kotlin.IllegalArgumentException


inline fun <reified T: Any> mock() = Mockito.mock(T::class.java)

@RunWith(MockitoJUnitRunner.Silent::class)
class FirestoreMeasurementRepoTests {

    @Mock
    private var statsColl: CollectionReference? = null

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
        repo = FirestoreMeasurementRepository(statsColl!!, logger)
    }

    @Test
    fun getLastSuccessTest(){
        val meas1 = Measurement(10.0f, Date(), "TEMPERATURE")
        val meas2 = Measurement(20.0f, Date(), "TEMPERATURE")

        val snapshot = Mockito.mock(QuerySnapshot::class.java)
        Mockito.`when`(snapshot.toObjects(Measurement::class.java)).thenReturn(listOf(meas1, meas2))
        val query = Mockito.mock(Query::class.java)
        Mockito.`when`(query.orderBy(Mockito.anyString(), Mockito.any())).thenReturn(query)
        Mockito.`when`(query.addSnapshotListener(Mockito.any())).then {
            var res = it.arguments[0] as EventListener<QuerySnapshot>
            res.onEvent(snapshot, null)
            mock<ListenerRegistration>()
        }
        Mockito.`when`(statsColl!!.whereEqualTo(Mockito.anyString(), Mockito.anyString())).thenReturn(query)
        repo!!.listenMeasurementBySensor(SensorType.TEMPERATURE, null, object: OnRepositoryResult<List<Measurement>>{
            override fun onSuccessResult(result: List<Measurement>) {
                Assert.assertTrue(result.size == 2)
            }

            override fun onError(e: Exception) {
                throw e
            }
        })
    }


    @Test(expected = IllegalArgumentException::class)
    fun getLastSuccess_SkipInvalidTest(){
        repo!!.listenMeasurementBySensor(SensorType.UNKNOWN, 2, object:OnRepositoryResult<List<Measurement>>{
            override fun onSuccessResult(result: List<Measurement>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: Exception) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

/*
    @Test
    fun getLastSuccess_Error(){
        val meas1 = Measurement(10.0f, Date(), "TEMPERATURE")
        val meas2 = Measurement(20.0f, Date(), "TEMPERATURE")
        var exception = FirebaseFirestoreException("papas", FirebaseFirestoreException.Code.ABORTED)

        val snapshot = Mockito.mock(QuerySnapshot::class.java)
        Mockito.`when`(snapshot.toObjects(Measurement::class.java)).thenReturn(listOf(meas1, meas2))
        val query = Mockito.mock(Query::class.java)
        Mockito.`when`(query.orderBy(Mockito.anyString(), Mockito.any())).thenReturn(query)
        Mockito.`when`(query.addSnapshotListener(Mockito.any())).then {
            var res = it.arguments[0] as EventListener<QuerySnapshot>
            res.onEvent(snapshot, FirebaseFirestoreException("papas", exception.code))
            mock<ListenerRegistration>()
        }
        Mockito.`when`(statsColl!!.whereEqualTo(Mockito.anyString(), Mockito.anyString())).thenReturn(query)
        repo!!.listenMeasurementBySensor(SensorType.TEMPERATURE, null, object: OnRepositoryResult<List<Measurement>>{
            override fun onSuccessResult(result: List<Measurement>) {
                throw NotImplementedError()
            }

            override fun onError(e: Exception) {
                Assert.assertEquals(exception, e)
            }
        })
    }
    */
}