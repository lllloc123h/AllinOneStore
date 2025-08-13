export function formatDate(dateTimeString) {
  const date = new Date(dateTimeString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

export function formatDateTimeLocal(dateTimeString) {
  const date = new Date(dateTimeString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day}T${hours}:${minutes}:00.00`
}
export function toISOStringWithTimezone(datetimeLocalString) {
  const date = new Date(datetimeLocalString)
  return date.toISOString()
}

// Gender options for radio buttons
export function getGenderOptions() {
  return [
    { value: true, label: 'Nam' },
    { value: false, label: 'Nữ' }
  ]
}

// Format gender value for display
export function formatGender(genderValue) {
  if (genderValue === true) return 'Nam'
  if (genderValue === false) return 'Nữ'
  return 'Chưa xác định'
}

// Get gender icon
export function getGenderIcon(genderValue) {
  if (genderValue === true) return '👨'
  if (genderValue === false) return '👩'
  return '⚧'
}