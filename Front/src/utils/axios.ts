import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';

const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
});

api.interceptors.request.use(
  (config: AxiosRequestConfig<any>) => {
    const jwt = localStorage.getItem('jwt');

    if (jwt) {
      if (!config.headers) {
        config.headers = {};
      }
      config.headers['Authorization'] = 'Bearer ' + jwt;
    } else {
      navigateTo('/error/NoLoggued')
      throw new Error('No se proporcionó un token JWT');
      
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
