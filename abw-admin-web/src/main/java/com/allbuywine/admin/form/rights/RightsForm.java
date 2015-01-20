package com.allbuywine.admin.form.rights;

import com.allbuywine.admin.bean.domain.Rights;

import javax.validation.constraints.NotNull;

/**
 * hufeng on 14-6-30.
 * Description：权限form
 */
public class RightsForm {
    /**
     * 权限id
     */
    private Integer id;

    /**
     * 权限父id
     */
    @NotNull(message = "父目录id不能为空，默认为0")
    private Integer parentId;

    /**
     * 权限名称
     */
    @NotNull(message = "权限名称不能为空")
    private String name;

    /**
     * 权限url
     */
//    @NotBlank(message = "权限url不能为空")
    private String url;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 是否在导航中显示 0：不显示 1：显示
     */
    private Integer isShow;


    //~~~~~~~~~~~~~~setter&&getter~~~~~~~~~~~~~
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    /**
     * 转化为Rights
     *
     * hufeng(of730)
     * @return
     */
    public Rights toRights() {
        Rights rights = new Rights();
        rights.setId(id);
        rights.setName(name);
        rights.setParentId(parentId);
        rights.setUrl(url);
        rights.setDescription(description);
        rights.setIsShow(isShow);

        return rights;
    }
}
