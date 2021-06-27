package com.enike.fudedu.ui.createNewClass

data class GroupModel(
    var groupName: String,
    var groupDescription: String,
    var groupCreator: String, var groupMembers: MutableList<String>
)