package com.example.retrofittutokotlin.entities

class Pokemon {

    private lateinit var name : String
    private lateinit var url : String

    fun getName() : String{
        return name
    }

    fun setName(name : String){
        this.name = name
    }

    fun getUrl() : String{
        return url
    }

    fun setUrl(url : String){
        this.url = url
    }

}