import axios from 'axios'

const HTTP = axios.create({
  withCredentials: true,
  baseURL: import.meta.env.VITE_VUE_APP_SERVER_URL
})

HTTP.interceptors.response.use(
  function (response) {
    return response
  },
  function (error) {
    return Promise.reject(error)
  }
)
export default HTTP
