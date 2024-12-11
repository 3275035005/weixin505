import defaultSettings from '@/settings'

const title = defaultSettings.title || '教学反馈后台系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
