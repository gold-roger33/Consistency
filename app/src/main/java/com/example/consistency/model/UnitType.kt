package com.example.consistency.model

enum class UnitType(val label: String, val type: Type) {
    MINUTES("minutes", Type.TIME),
    HOURS("hours",Type.TIME),
    SESSIONS("sessions",Type.COUNT),
    PROBLEM("Problem",Type.COUNT),
    REPS("Reps",Type.COUNT),
    TASKS("Tasks",Type.COUNT),
    TIMES("TIMES",Type.COUNT),
    PAGES("Pages",Type.COUNT),
    OTHERS("Others",Type.COUNT);

    companion object {
        fun getTypeValue(type: Type): List<UnitType>{
            return UnitType.entries.filter {
                it.type == type
            }
        }
    }
}

