package com.allbuywine.admin.bean.domain;

/**
 * hufeng on 14-6-30.
 * Description：
 */
public class Rights extends BaseBean
{
    /**
     * 权限id
     */
    private Integer id;

    /**
     * 权限父id
     */
    private Integer parentId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限url
     */
    private String url;

    /**
     * 权限简单描述
     */
    private String description;

    /**
     * 是否在导航中显示 0：不显示 1：显示
     */
    private Integer isShow;


    //~~~~~~~~~~~~~setter&&getter~~~~~~~~~~
    public Integer getId ()
    {
        return id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public Integer getParentId ()
    {
        return parentId;
    }

    public void setParentId (Integer parentId)
    {
        this.parentId = parentId;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public Integer getIsShow ()
    {
        return isShow;
    }

    public void setIsShow (Integer isShow)
    {
        this.isShow = isShow;
    }
}
