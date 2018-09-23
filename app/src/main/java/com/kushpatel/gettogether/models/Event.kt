package com.kushpatel.gettogether.models

class Event{

    var uid: String? = null
    var eventName: String? = null
    var address: String? = null

    constructor(){

    }

    constructor(uid: String, eventName: String, address: String){
        this.uid = uid
        this.eventName = eventName
        this.address = address
    }

}