package ${package}.model;

<#if config.enableLombok>
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
</#if>
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
<#list columns as column>
<#if column.javaType == "LocalDateTime">
import java.time.LocalDateTime;
<#break>
</#if>
</#list>
<#list columns as column>
<#if column.javaType == "LocalDate">
import java.time.LocalDate;
<#break>
</#if>
</#list>
<#list columns as column>
<#if column.javaType == "BigDecimal">
import java.math.BigDecimal;
<#break>
</#if>
</#list>

/**
 * ${tableName} 表对应的实体类
 */
<#if config.enableLombok>
@Data
@AllArgsConstructor
@NoArgsConstructor
</#if>
public class ${className} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list columns as column>
    /**
     * ${column.comment!column.name}
     */
    <#if column.primaryKey>
    @TableId(value = "${column.fieldName}", type = IdType.AUTO)
    </#if>
    private ${column.javaType} ${column.name};

</#list>

<#if !config.enableLombok>
    // 如果未启用 Lombok，生成 getter/setter 方法
    <#list columns as column>
    public ${column.javaType} get${column.name?cap_first}() {
        return ${column.name};
    }

    public void set${column.name?cap_first}(${column.javaType} ${column.name}) {
        this.${column.name} = ${column.name};
    }

    </#list>
</#if>
}
