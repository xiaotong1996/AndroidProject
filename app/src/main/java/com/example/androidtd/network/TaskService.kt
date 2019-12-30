package com.example.androidtd.network

import com.example.androidtd.Data.Task
import retrofit2.Response
import retrofit2.http.*

interface TasksService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String): Response<String>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PATCH("tasks/{id}")
    suspend fun updateTask(@Body task: Task): Response<Task>

    @PATCH("tasks/{id}/mark_as_done")
    suspend fun markAsDoneTask(@Path("id") id: String): Response<Task>
}