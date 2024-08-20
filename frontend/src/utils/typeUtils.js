import {isEqual, toInteger} from "lodash";

function contain(list, item) {
    for (let sub in list) {
        if (isEqual(list[sub], item)) {
            return true
        }
    }
    return false
}

/**
 * 将指定分隔符连接的数字组成的字符串转换成数组
 * @param string :string
 * @param seperator :string
 */
function string2ints(string, seperator) {
    let ints = []
    const list = string.split(seperator)
    for (let key in list) {
        ints.push(toInteger(list[key]))
    }
    return ints
}

export {contain, string2ints}