package de.ude.openlap.xapi.dto;

import java.io.Serializable;

public class QueryParameters implements Serializable {
	private static final long serialVersionUID = -1764970284520387975L;

	private Object[] queryOptionalParameters;
	private Object parametersToBeReturnedInResult;
	private Object[] queryMandatoryParameters;


	public Object[] getQueryMandatoryParameters() {
		return queryMandatoryParameters;
	}

	public void setQueryMandatoryParameters(Object[] queryMandatoryParameters) {
		this.queryMandatoryParameters = queryMandatoryParameters;
	}

	public QueryParameters() {
    }

	public Object getParametersToBeReturnedInResult() {
		return parametersToBeReturnedInResult;
	}

	public void setParametersToBeReturnedInResult(Object parametersToBeReturnedInResult) {
		this.parametersToBeReturnedInResult = parametersToBeReturnedInResult;
	}

	public Object[] getQueryOptionalParameters() {
		return queryOptionalParameters;
	}

	public void setQueryOptionalParameters(Object[] queryOptionalParameters) {
		this.queryOptionalParameters = queryOptionalParameters;
	}


}
