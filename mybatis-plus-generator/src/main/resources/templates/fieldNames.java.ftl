package ${package.Mapper}.table_field_name;

<#list table.importPackages as pkg>
  import ${pkg};
</#list>

/**
* <p>
  * ${table.comment!}
  * </p>
*
* @author ${author}
* @since ${date}
*/
public interface ${entity}FieldNames {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
  /**
  * ${field.comment}
  */
  String ${field.propertyName} = "${field.name}";
</#list>
<#------------  END 字段循环遍历  ---------->
}