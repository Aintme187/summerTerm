<template>
  <div>
    <div class="panel">
      <el-table
        :data="timetable"
        :span-method="objectSpanMethod"
        border
        :header-cell-style="headerCellStyle"
        :cell-style="tableCellStyle"
      >
        <el-table-column prop="sjd" label="时间段" width="80" align="center">
        </el-table-column>
        <el-table-column prop="jc" label="节次" width="150" align="center">
        </el-table-column>
        <el-table-column prop="mon" label="星期一" align="center">
          <template #default="scope">
            <h4>{{ scope.row.mon.name }}</h4>
            <div v-html="scope.row.mon.content"></div>
          </template>
        </el-table-column>
        <el-table-column prop="tue" label="星期二" align="center">
          <template #default="scope">
            <h4>{{ scope.row.tue.name }}</h4>
            <div v-html="scope.row.tue.content"></div>
          </template>
        </el-table-column>
        <el-table-column prop="wed" label="星期三" align="center">
          <template #default="scope">
            <h4>{{ scope.row.wed.name }}</h4>
            <div v-html="scope.row.wed.content"></div>
          </template>
        </el-table-column>
        <el-table-column prop="thu" label="星期四" align="center">
          <template #default="scope">
            <h4>{{ scope.row.thu.name }}</h4>
            <div v-html="scope.row.thu.content"></div>
          </template>
        </el-table-column>
        <el-table-column prop="fri" label="星期五" align="center">
          <template #default="scope">
            <h4>{{ scope.row.fri.name }}</h4>
            <div v-html="scope.row.fri.content"></div>
          </template>
        </el-table-column>
        <el-table-column prop="sat" label="星期六" align="center">
          <template #default="scope">
            <h4>{{ scope.row.sat.name }}</h4>
            <div v-html="scope.row.sat.content"></div>
          </template>
        </el-table-column>
        <el-table-column prop="sun" label="星期日" align="center">
          <template #default="scope">
            <h4>{{ scope.row.sun.name }}</h4>
            <div v-html="scope.row.sun.content"></div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    // 下午节次数
    afternoonLength: {
      type: [String, Number],
      default: 5
    },
    // 总节次
    length: {
      type: [String, Number],
      default: 14
    },
    // 课表数据
    events: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      // 课程表数据
      timetable: [],
      hoverOrderArr: [],
      weeks: ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'],
      todayWeek: null,
    }
  },
  mounted() {
    this.mergeData()
  },
  watch: {
    events: {
      // eslint-disable-next-line no-unused-vars
      handler(newVal, oldVal) {
        this.mergeData()
      },
      deep: true
    }
  },
  created() {
    this.makeTimetable();
    this.todayWeek = this.getWeek(); // 获取当前是星期几
  },
  methods: {
    // 表头添加背景色
    headerCellStyle({ column, columnIndex }) {
      // 根据columnIndex来确定是星期几的表头
      const weekDay = this.weeks[columnIndex-1];
      // 如果当前表头对应的是今天，返回红色背景
      if (weekDay === this.todayWeek) {
        return {
          background: 'darkred',
          color: 'white',
          fontWeight: 1000
        };
      }
      // 否则，使用默认的表头样式
      return {
        background: '#d9e5fd',
        color: 'black',
        fontWeight: 1000
      };
    },
    getWeek() {
      // 获取当前是星期几，返回'mon', 'tue', ..., 'sun'
      const today = new Date();
      const dayIndex = today.getDay(); // getDay()返回0-6，其中0是星期日
      return this.weeks[(dayIndex === 0 ? 7 : dayIndex)];
    },
    // 单元格添加背景色
    // eslint-disable-next-line no-unused-vars
    tableCellStyle({ row, column, rowIndex, columnIndex }) {
      if (row[column.property].name !== undefined) {
        const weekDay = this.weeks[columnIndex-1];
        // 如果当前表头对应的是今天，返回红色背景
        if (weekDay === this.todayWeek) {
          return {
            'background-color': 'red',
            color: '#fff',
            'border-radius': '10px'
          };
        }
        return {
          'background-color': 'rgb(24 144 255 / 80%)',
          color: '#fff',
          'border-radius': '10px'
        }
      }
    },
    // 构造课程表完整数据
    makeTimetable() {
      this.timetable = []
      let jcMap = {
        1: '1 ' + '(8:00-8:45)',
        2: '2 ' + '(8:50-9:35)',
        3: '3 ' + '(9:50-10:35)',
        4: '4 ' + '(10:40-11:25)',
        5: '5 ' + '(11:30-12:15)',
        6: '6 ' + '(14:00-14:45)',
        7: '7 ' + '(14:50-15:35)',
        8: '8 ' + '(15:50-16:35)',
        9: '9 ' + '(16:40-17:25)',
        10: '10 ' + '(17:30-18:15)',
        11: '11 ' + '(19:00-19:45)',
        12: '12 ' + '(19:50-20:35)',
        13: '13 ' + '(20:40-21:25)',
        14: '14 ' + '(21:30-22:15)',
      }
      for (let i = 0; i < this.length; i++) {
        let one = {
          sjd: '',
          jc: jcMap[i+1],
          mon: {},
          tue: {},
          wed: {},
          thu: {},
          fri: {},
          sat: {},
          sun: {}
        }
        if (i < 5) {
          one.sjd = '上午'
        } else if (i > 4 && i < this.afternoonLength + 5) {
          one.sjd = '下午'
        } else {
          one.sjd = '晚上'
        }
        this.timetable.push(one)
      }
    },
    mergeData() {
      // 合并数据
      if (this.events && this.events.length > 0) {
        for (let i = 0; i < this.events.length; i++) {
          // 获取星期几
          let week = this.weeks[this.events[i].day - 1]
          this.timetable[this.events[i].sectionBegin - 1][week] = this.events[i]
        }
      }
    },
    // eslint-disable-next-line no-unused-vars
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex < 5) {
          if (rowIndex === 0) {
            return {
              rowspan: 5,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        } else if (rowIndex > 4 && rowIndex < 5 + this.afternoonLength) {
          if (rowIndex === 5) {
            return {
              rowspan: this.afternoonLength,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        } else {
          if (rowIndex === 5 + this.afternoonLength) {
            return {
              rowspan: this.length - 5 - this.afternoonLength,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        }
      }
      if (columnIndex === 2) {
        if (row.mon.name !== undefined) {
          return {
            rowspan: row.mon.sectionEnd - row.mon.sectionBegin + 1,
            colspan: 1
          }
        } else {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
      if (columnIndex === 3) {
        if (row.tue.name !== undefined) {
          return {
            rowspan: row.tue.sectionEnd - row.tue.sectionBegin + 1,
            colspan: 1
          }
        } else {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
      if (columnIndex === 4) {
        if (row.wed.name !== undefined) {
          return {
            rowspan: row.wed.sectionEnd - row.wed.sectionBegin + 1,
            colspan: 1
          }
        } else {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
      if (columnIndex === 5) {
        if (row.thu.name !== undefined) {
          return {
            rowspan: row.thu.sectionEnd - row.thu.sectionBegin + 1,
            colspan: 1
          }
        } else {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
      if (columnIndex === 6) {
        if (row.fri.name !== undefined) {
          return {
            rowspan: row.fri.sectionEnd - row.fri.sectionBegin + 1,
            colspan: 1
          }
        } else {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
      if (columnIndex === 7) {
        if (row.sat.name !== undefined) {
          return {
            rowspan: row.sat.sectionEnd - row.sat.sectionBegin + 1,
            colspan: 1
          }
        } else {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
      if (columnIndex === 8) {
        if (row.sun.name !== undefined) {
          return {
            rowspan: row.sun.sectionEnd - row.sun.sectionBegin + 1,
            colspan: 1
          }
        } else {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
    }
  }
}
</script>
