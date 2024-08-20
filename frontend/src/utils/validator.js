import isEmpty from "validator/es/lib/isEmpty.js";
import isEmail from "validator/es/lib/isEmail.js";
import isMobilePhone from "validator/es/lib/isMobilePhone.js";
import {isNumber} from "lodash";
import {contain} from "@/utils/typeUtils.js"

/**
 * bug here
 * 账号密码检查条件
 */

/**
 * 检查账号是否合法
 * 参数符合 el-form validator 格式
 * @param rule
 * @param value
 * @param callback
 */
function isLegalAccount(rule, value, callback) {
    let reg = RegExp("^\\w{5,20}$")
    if (isEmpty(value, {"ignore_whitespace": true})) {
        callback(new Error("账号不能为空"))
    } else if (!reg.test(value)) {
        callback(new Error("账号由5-20位数字字母下划线组成"))
    } else {
        callback()
    }
}

function isLegalPassword(rule, value, callback) {
    let reg = RegExp("^[\\w@#$%]{5,20}$")
    if (!value || value.trim() === "") {
        callback(new Error("密码不能为空"))
    } else if (!reg.test(value)) {
        callback(new Error("密码由5-20位数字字母下划线或@#$%组成")) .account
    } else {
        callback()
    }
}

function isLegalNickname(rule, value, callback) {
    let reg = RegExp("^[^!@#$%^&*()<>?,./]+$")
    if (value.trim() === "") {
        callback(new Error("昵称不能为空"))
    } else if (!reg.test(value)) {
        callback(new Error("昵称不能包含\"!@#$%^&*()<>?,./\"等特殊字符"))
    } else {
        callback()
    }
}

function isLegalEmail(rule, value, callback) {
    if (value.trim() !== "" && !isEmail(value)) {
        callback(new Error("请输入有效的邮箱地址"))
    } else {
        callback()
    }
}

function isLegalMobilePhoneNumber(rule, value, callback) {
    if (value.trim() !== "" && !isMobilePhone(value, "zh-CN")) {
        callback(new Error("请输入有效的电话号码"))
    } else {
        callback()
    }
}

function isLegalPermission(rule, value, callback) {
    if (value.trim() === "") {
        callback(new Error("请选择身份"))
    } else {
        callback()
    }
}

function isLegalCurriculumName(rule, value, callback) {
    let reg = RegExp(/^[^,]*$/)
    if (value.trim() === "") {
        callback(new Error("课程名称不能为空"))
    } else if (!reg.test(value)) {
        callback(new Error("课程名称中不能包含','"))
    } else {
        callback()
    }
}

function isLegalDeptName(rule, value, callback) {
    if (value.trim() === "") {
        callback()
    } else if (!contain(deptNames, value)) {
        callback(new Error("不合法的学院"))
    } else {
        callback()
    }
}

function isLegalCredit(rule, value, callback) {
    if (!isNumber(value)) {
        callback(new Error("学分为小数"))
    } else if (value <= 0) {
        callback(new Error("学分应大于0"))
    } else {
        callback()
    }
}

function isLegalCapacity(rule, value, callback) {
    if (!isNumber(value)) {
        callback(new Error("容量为整数"))
    } else if (value <= 0) {
        callback(new Error("容量应大于0"))
    } else {
        callback()
    }
}

function isLegalIntroduction(rule, value, callback) {
    if (value.length > 200) {
        callback(new Error("课程简介不能超过200字"))
    } else {
        callback()
    }
}

function isLegalId(rule, value, callback) {
    if (!isNumber(value)) {
        callback(new Error("id为整数"))
    } else {
        callback()
    }
}

function isLegalWeek(rule, value, callback) {
    if (!isNumber(value) || value < 1) {
        callback(new Error("请输入不小于1的整数"))
    } else {
        callback()
    }
}

function isLegalDay(rule, value, callback) {
    if (!isNumber(value) || value < 1 || value > 7) {
        callback(new Error("请输入不小于1不大于7的整数"))
    } else {
        callback()
    }
}

function isLegalSection(rule, value, callback) {
    if (!isNumber(value) || value < 1 || value > 13) {
        callback(new Error("请输入不小于1不大于13的整数"))
    } else {
        callback()
    }
}

function isLegalRoom(rule, value, callback) {
    if (value.length > 30) {
        callback(new Error("上课地点不能超过30字"))
    } else {
        callback()
    }
}

function isLegalSignature(rule, value, callback) {
    if (value.length > 200) {
        callback(new Error("个性签名不能超过30字"))
    } else {
        callback()
    }
}

const deptNames = [
    "安全科学学院", "材料学院", "电机工程与应用电子技术系", "法学院", "工程物理系", "公共管理学院",
    "国家卓越工程师学院", "航天航空学院", "核能与新能源技术研究院", "化学工程系", "环境学院", "计算机学院",
    "机械工程学院", "能源与动力工程系", "车辆与运载学院", "建筑学院", "教育研究院", "经济管理学院",
    "理学院", "马克思主义学院", "美术学院", "人文学院", "软件工程学院", "社会科学学院",
    "生命科学学院", "生物医学交叉研究院", "土木水利学院", "新闻与传播学院", "信息科学技术学院", "药学院",
    "医学院",
]

const rules = {
    "account": {"required": true, "trigger": "blur", "validator": isLegalAccount},
    "password": {"required": true, "trigger": "blur", "validator": isLegalPassword},
    "nickname": {"required": true, "trigger": "blur", "validator": isLegalNickname},
    "email": {"trigger": "blur", "validator": isLegalEmail},
    "mobilePhoneNumber": {"trigger": "blur", "validator": isLegalMobilePhoneNumber},
    "permission": {"required": true, "trigger": "blur", "validator": isLegalPermission},
    "signature": {"trigger": "blur", "validator": isLegalSignature},

    "name": {"required": true, "trigger": "blur", "validator": isLegalCurriculumName},
    "credit": {"required": true, "trigger": "blur", "validator": isLegalCredit},
    "introduction": {"trigger": "blur", "validator": isLegalIntroduction},

    "curriculumId": {"required": true, "trigger": "blur", "validator": isLegalId},
    "capacity": {"required": true, "trigger": "blur", "validator": isLegalCapacity},
    "teacherId": {"required": true, "trigger": "blur", "validator": isLegalId},
    "deptName": {"trigger": "blur", "validator": isLegalDeptName},
    "weekBegin": {"required": true, "trigger": "blur", "validator": isLegalWeek},
    "weekEnd": {"required": true, "trigger": "blur", "validator": isLegalWeek},
    "day": {"required": true, "trigger": "blur", "validator": isLegalDay},
    "sectionBegin": {"required": true, "trigger": "blur", "validator": isLegalSection},
    "sectionEnd": {"required": true, "trigger": "blur", "validator": isLegalSection},
    "room": {"trigger": "blur", "validator": isLegalRoom},
    // free 用于不需要检查的字段
    "free": {}
}

export {rules, deptNames}