import { defineStore } from 'pinia'
import { StoreId } from './storeId'


export const useUserStore = defineStore(StoreId.USER, {
    state: () => {
        return {
            nickname: '',
            uid: 0,
            username: '',
        }
    },
    actions: {
        setUser(nickname: string, uid: number, username: string) {
            this.nickname = nickname
            this.uid = uid
            this.username = username
        }
    }
})