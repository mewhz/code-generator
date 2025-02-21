package ${package}.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package}.model.${className};
import ${package}.service.${className}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * ${tableName} 表对应的 Controller
 */
@RestController
@RequestMapping("/${className?uncap_first}")
@RequiredArgsConstructor
public class ${className}Controller {

    private final ${className}Service ${className?uncap_first}Service;

    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    public Page<${className}> queryPage(
            @RequestParam(value = "current", defaultValue = "1") Long current,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return ${className?uncap_first}Service.queryPage(current, size);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public ${className} queryById(@PathVariable Long id) {
        return ${className?uncap_first}Service.queryById(id);
    }

    /**
     * 新增数据
     */
    @PostMapping
    public boolean insert(@RequestBody ${className} ${className?uncap_first}) {
        return ${className?uncap_first}Service.insert(${className?uncap_first});
    }

    /**
     * 修改数据
     */
    @PutMapping
    public boolean update(@RequestBody ${className} ${className?uncap_first}) {
        return ${className?uncap_first}Service.update(${className?uncap_first});
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return ${className?uncap_first}Service.delete(id);
    }
} 