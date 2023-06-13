import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';

const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
});

api.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    const jwt = localStorage.getItem('jwt');

    if (jwt) {
      config.headers['Authorization'] = 'Bearer ' + jwt;
    } else {
      navigateTo('/login');
      return Promise.reject(new Error('No se proporcionó un token JWT'));
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
