package com.maciel.murillo.musales.data.model

import com.maciel.murillo.musales.domain.model.State

enum class StateData {

    AC,
    AL,
    AM,
    AP,
    BA,
    CE,
    DF,
    ES,
    GO,
    MA,
    MG,
    MS,
    MT,
    PA,
    PB,
    PE,
    PI,
    PR,
    RJ,
    RN,
    RO,
    RR,
    RS,
    SC,
    SE,
    SP,
    TO,
}

fun State.mapToStateData() = when (this) {
    State.AC -> StateData.AC
    State.AL -> StateData.AL
    State.AM -> StateData.AM
    State.AP -> StateData.AP
    State.BA -> StateData.BA
    State.CE -> StateData.CE
    State.DF -> StateData.DF
    State.ES -> StateData.ES
    State.GO -> StateData.GO
    State.MA -> StateData.MA
    State.MG -> StateData.MG
    State.MS -> StateData.MS
    State.MT -> StateData.MT
    State.PA -> StateData.PA
    State.PB -> StateData.PB
    State.PE -> StateData.PE
    State.PI -> StateData.PI
    State.PR -> StateData.PR
    State.RJ -> StateData.RJ
    State.RN -> StateData.RN
    State.RO -> StateData.RO
    State.RR -> StateData.RR
    State.RS -> StateData.RS
    State.SC -> StateData.SC
    State.SE -> StateData.SE
    State.SP -> StateData.SP
    State.TO -> StateData.TO
}