package com.example.cyberspace_info.listaeventosnaturais.model

data class EventoNaturalContainerModel(

   var  title: String,
   var description: String,
   var link: String,
   var events: List<EventNaturalModel>
)