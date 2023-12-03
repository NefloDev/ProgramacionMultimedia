package com.example.retrofittutokotlin.entities

class ApiResponse () {
    private lateinit var results : List<Pokemon>
    fun getResults() : List<Pokemon>{
        return results
    }

    fun setResults(results : List<Pokemon>){
        this.results = results
    }
}