package com.rakuten.ross.auroraj.application;

import com.rakuten.ross.auroraj.domain.AtrSummary;

public interface Agent {

    AtrSummary readXtrDocument(String userInput) throws AgentException;

}
