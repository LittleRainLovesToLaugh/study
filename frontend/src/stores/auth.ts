import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { loginApi, meApi } from '@/api/modules'
import type { LoginPayload } from '@/types/api'

const TOKEN_KEY = 'commerce_token'
const USER_KEY = 'commerce_user'

interface AuthUser {
  userId: number
  username: string
  displayName?: string
  roleCode: string
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string>(localStorage.getItem(TOKEN_KEY) || '')
  const user = ref<AuthUser | null>(readUser())

  const isLoggedIn = computed(() => Boolean(token.value))

  function readUser(): AuthUser | null {
    const raw = localStorage.getItem(USER_KEY)
    if (!raw) return null
    try {
      return JSON.parse(raw) as AuthUser
    } catch {
      return null
    }
  }

  function persist() {
    if (token.value) {
      localStorage.setItem(TOKEN_KEY, token.value)
    } else {
      localStorage.removeItem(TOKEN_KEY)
    }
    if (user.value) {
      localStorage.setItem(USER_KEY, JSON.stringify(user.value))
    } else {
      localStorage.removeItem(USER_KEY)
    }
  }

  async function login(payload: LoginPayload) {
    const data = await loginApi(payload)
    token.value = data.token
    user.value = {
      userId: data.userId,
      username: data.username,
      displayName: data.displayName,
      roleCode: data.roleCode,
    }
    persist()
  }

  async function fetchMe() {
    if (!token.value) return
    const data = await meApi()
    user.value = {
      userId: data.userId,
      username: data.username,
      displayName: user.value?.displayName,
      roleCode: data.roleCode,
    }
    persist()
  }

  function logout() {
    token.value = ''
    user.value = null
    persist()
  }

  return { token, user, isLoggedIn, login, fetchMe, logout }
})
