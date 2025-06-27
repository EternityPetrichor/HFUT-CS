const MONTHS = [
    '一月', '二月', '三月', '四月',
    '五月', '六月', '七月', '八月',
    '九月', '十月', '十一月', '十二月'
];

// const HOLIDAYS = [1, 7]; // 假设1月1日和10月1日为节假日

const HOLIDAYS = {
    '1-1': '元旦',
    '10-1': '国庆节'
};

function isHoliday(date) {
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const holidayKey = `${month}-${day}`;
    return HOLIDAYS.hasOwnProperty(holidayKey);
}

function getHolidayName(date) {
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const holidayKey = `${month}-${day}`;
    return HOLIDAYS[holidayKey];
}

// ...

function renderCalendar() {
    // ...

            if (HOLIDAYS[date]) {
                cell.classList.add('holiday');
                const holidayName = getHolidayName(new Date(currentYear, currentMonth, date));
                const holidaySpan = document.createElement('span');
                holidaySpan.textContent = holidayName;
                cell.appendChild(holidaySpan);
            }

    // ...
}


let currentMonth = new Date().getMonth();
let currentYear = new Date().getFullYear();

function renderCalendar() {
    const calendarBody = document.getElementById('calendar-body');
    const monthYear = document.getElementById('month-year');

    monthYear.textContent = `${MONTHS[currentMonth]} ${currentYear}`;

    const firstDay = new Date(currentYear, currentMonth, 1).getDay();
    const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

    calendarBody.innerHTML = '';

    let date = 1;

    for (let i = 0; i < 6; i++) {
        const row = document.createElement('tr');

        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay) {
                const cell = document.createElement('td');
                row.appendChild(cell);
            } else if (date > daysInMonth) {
                break;
            } else {
                const cell = document.createElement('td');
                cell.textContent = date;

                if (HOLIDAYS.includes(date)) {
                    cell.classList.add('holiday');
                }

                row.appendChild(cell);
                date++;
            }
        }

        calendarBody.appendChild(row);
    }
}

function previousMonth() {
    currentMonth--;
    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    }
    renderCalendar();
}

function nextMonth() {
    currentMonth++;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    renderCalendar();
}
]
renderCalendar();