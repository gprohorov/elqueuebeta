const api_protocol  = 'http',
      api_host      = window.location.hostname,
      api_port      = '8077',
      api_suffix    = '/api';
export const config = {
  api_path: api_protocol + '://' + api_host + ':' + api_port + api_suffix,
  project: 'mg'
};
