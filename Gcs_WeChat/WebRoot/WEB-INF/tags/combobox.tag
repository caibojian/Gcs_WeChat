<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"
	trimDirectiveWhitespaces="true"%>
<%@ attribute name="id" type="java.lang.String"%>
<%@ attribute name="name" type="java.lang.String"%>
<%@ attribute name="options" type="java.lang.String"%>
<%@ attribute name="style" type="java.lang.String"%>
<%@ attribute name="type" type="java.lang.String"%>

<input id="${id}" class="easyui-combobox" name="${name}" style="${style}" type="${type}" data-options="${options}" />


