package de.ude.openlap.xapi.dto;

import java.io.Serializable;

public class QueryParameters implements Serializable {
	private static final long serialVersionUID = -1764970284520387975L;

	private Object[] query;
	private Object parametersToBeReturnedInResult;
	private Object[] statementDuration;




	public QueryParameters() {
    }

	public Object getParametersToBeReturnedInResult() {
		return parametersToBeReturnedInResult;
	}

	public void setParametersToBeReturnedInResult(Object parametersToBeReturnedInResult) {
		this.parametersToBeReturnedInResult = parametersToBeReturnedInResult;
	}

	public Object[] getQuery() {
		return query;
	}

	public void setQuery(Object[] query) {
		this.query = query;
	}

	public Object[] getStatementDuration() {
		return statementDuration;
	}

	public void setStatementDuration(Object[] statementDuration) {
		this.statementDuration = statementDuration;
	}


}
