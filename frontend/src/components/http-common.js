import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: `http://localhost:8088/api`,
  headers: {
    'Access-Control-Allow-Origin': 'http://localhost:9528'
  }
});