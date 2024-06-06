export function getNowFormatDate() {
    let date = new Date(),
        year = date.getFullYear(), //获取完整的年份(4位)
        month = date.getMonth() + 1, //获取当前月份(0-11,0代表1月)
        strDate = date.getDate() // 获取当前日(1-31)  
    return `${year}-` + (month < 10 ? `0${month}-` : `${month}-`) + (strDate < 10 ? `0${strDate}` : `${strDate}`)
}