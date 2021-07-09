package com.enike.fudedu.ui.createNewClass

import java.util.ArrayList

data class GroupModel(
    val groupName: String,
    var groupDescription: String ,
    var groupCreator: String,
    var groupMembers: ArrayList<String>)