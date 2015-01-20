package com.allbuywine.admin.bean.domain;

/**
 * hufeng on 14-7-1.
 * Description：部门pojo
 */
public class Dept extends BaseBean
{
    /**
     * 部门id
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门描述
     */
    private String description;


    //~~~~~~~~~~~~~~setter&&getter~~~~~~~~~~~~~~
    public Integer getId ()
    {
        return id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }
}
