function easterSunday(year: number): Date {
    const a = year % 19
    const b = Math.floor(year / 100)
    const c = year % 100
    const d = Math.floor(b / 4)
    const e = b % 4
    const f = Math.floor((b + 8) / 25)
    const g = Math.floor((b - f + 1) / 3)
    const h = (19 * a + b - d - g + 15) % 30
    const i = Math.floor(c / 4)
    const k = c % 4
    const l = (32 + 2 * e + 2 * i - h - k) % 7
    const m = Math.floor((a + 11 * h + 22 * l) / 451)
    const month = Math.floor((h + l - 7 * m + 114) / 31) - 1
    const day = ((h + l - 7 * m + 114) % 31) + 1
    return new Date(year, month, day)
}

export function isEasterPeriod(): boolean {
    const today = new Date()
    const easter = easterSunday(today.getFullYear())

    // Palm Sunday (7 days before Easter) through Easter Monday (1 day after)
    const palmSunday = new Date(easter)
    palmSunday.setDate(easter.getDate() - 7)

    const easterMonday = new Date(easter)
    easterMonday.setDate(easter.getDate() + 1)

    return today >= palmSunday && today <= easterMonday
}

