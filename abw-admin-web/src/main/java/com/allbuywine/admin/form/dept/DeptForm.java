package com.allbuywine.admin.form.dept;

import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.core.util.Pagination;
import org.hibernate.validator.constraints.NotBlank;

/**
 * hufeng on 14-7-1.
 * Description：
 */
public class DeptForm extends Pagination
{
    /**
     * 部门id
     */
    private Integer id;
    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String name;
    /**
     * 部门描述
     */
    private String description;


    //~~~~~~~~~~~~~~setter&&getter~~~~~~~~~~
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * form转pojo
     * @return
     */
    public Dept toDept() {
        Dept dept = new Dept();
        dept.setId(id);
        dept.setName(name);
        dept.setDescription(description);

        return dept;
    }
}
