package com.example.lb5kot

class Processor(var type: String, var mz: Int){
    fun addToList(): String {
        var str: String = "Тип: " + type + " mz: " + mz.toString();
        return str;
    }
    fun getToList(): String {
        var str: String = "Вы выбрали процессор с типом: " + type + " mz: " + mz.toString();
        return str;
    }
}