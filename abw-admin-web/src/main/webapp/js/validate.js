/**
 * Created by of837_jh on 2014/4/23.
 */

/**
 * 非空验证
 * @author of837_jh
 *
 * @param value 被验证的值
 * @param name 值的名称
 * @param minLength 限制最小长度
 * @param maxLength 限制最大长度
 * @returns {boolean}
 */
function validateEmpty(value,name,minLength,maxLength){


    var flag = true;

    if($.trim(value) === ''){
        dialog_prompt({
            content:name + "不能为空！",
            width: 340,
            height: 180
        });
        flag = false;
    }

    var arr = value.split(",");

    $.each(arr,function(i,val){

        if($.trim(val).length < minLength || $.trim(val).length > maxLength){

            flag = false;
        }
    });


    if(!flag){
        dialog_prompt({
            content:name + "长度只能在" + minLength + "到" + maxLength + "之间！",
            width: 340,
            height: 180
        });
    }

    return flag;
}



/**
 * 验证规则： 中文/英文/数字 + 英文逗号 + 中文/英文/数字
 * @author of837_jh
 *
 * @param value 被验证的值
 * @returns {boolean}
 */
function validateSpecialChar(value,name){

    var pattern = new RegExp("^[A-Za-z0-9\u4e00-\u9fa5^s,()ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ]*[A-Za-z0-9\u4e00-\u9fa5^s()ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ]$");

    if(!pattern.test(value)){
        dialog_prompt({
            content:name + "不符合添加规则，添加多个属性值，使用英文的半角逗号隔开！",
            width: 340,
            height: 180
        });
        return false;
    }

    return true;
}

/**
 * 验证规则： 名称只能是中文/英文/数字
 * @author of837_jh
 *
 * @param value 被验证的值
 * @returns {boolean}
 */
function validateName(value,name){

    //var pattern = new RegExp("^[A-Za-z0-9\u4e00-\u9fa5^s]*$");
    var reg = /[&\\<>'"]/;
    if(reg.test(value)){
        dialog_prompt({
            content:name + "包含非法字符，请重新输入！",
            width: 340,
            height: 180
        });
        return false;
    }

    return true;
}

