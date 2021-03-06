package com.vladsch.kotlin.jdbc

import java.sql.CallableStatement
import java.sql.PreparedStatement

class SqlCall(
    statement: String,
    params: List<Any?> = listOf(),
    inputParams: Map<String, Any?> = mapOf(),
    outputParams: Map<String, Any> = mapOf()
) : SqlQuery(statement, params, inputParams) {

    protected val outputParams = HashMap(outputParams)

    override fun populateNamedParam(stmt: PreparedStatement, paramName: String, occurrences: List<Int>) {
        super.populateNamedParam(stmt, paramName, occurrences)

        if (outputParams.containsKey(paramName)) {
            // setup out or inout param
            (stmt as CallableStatement).registerOutParameter(paramName, outputParams[paramName].param().sqlType())
        }
    }

    override fun params(vararg params: Any?): SqlCall {
        return super.params(*params) as SqlCall
    }

    override fun paramsArray(params: Array<out Any?>): SqlCall {
        return super.paramsArray(params) as SqlCall
    }

    override fun paramsList(params: Collection<Any?>): SqlCall {
        return super.paramsList(params) as SqlCall
    }

    override fun inParams(params: Map<String, Any?>): SqlCall {
        return super.inParams(params) as SqlCall
    }

    override fun inParams(vararg params: Pair<String, Any?>): SqlCall {
        return super.inParams(*params) as SqlCall
    }

    override fun inParamsArray(params: Array<out Pair<String, Any?>>): SqlCall {
        return super.inParamsArray(params) as SqlCall
    }

    fun inOutParams(params: Map<String, Any?>): SqlCall {
        outputParams.putAll(params)
        inParams(params)
        return this
    }

    fun inOutParams(vararg params: Pair<String, Any?>): SqlCall {
        outputParams.putAll(params)
        inParamsArray(params)
        return this
    }

    fun outParams(params: Map<String, Any?>): SqlCall {
        outputParams.putAll(params)
        resetDetails()
        return this
    }

    fun outParams(vararg params: Pair<String, Any?>): SqlCall {
        outputParams.putAll(params)
        resetDetails()
        return this
    }

    override fun toString(): String {
        return "SqlCall(outputParams=$outputParams) ${super.toString()}"
    }
}
