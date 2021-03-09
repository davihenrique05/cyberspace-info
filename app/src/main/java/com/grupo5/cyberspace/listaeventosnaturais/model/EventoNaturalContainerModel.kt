package com.grupo5.cyberspace.listaeventosnaturais.model

data class EventoNaturalContainerModel(

   var  title: String,
   var description: String,
   var link: String,
   var events: List<EventNaturalModel>
)