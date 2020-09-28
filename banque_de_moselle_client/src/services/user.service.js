export const userService = {
  login,
  logout,
  userActuel
};

function userActuel() {
  const json = localStorage.getItem('user');
  return JSON.parse(json);
}

async function login(username, password) {
  const body = `username=${username}&password=${password}`;

  const requestOptions = {
    method: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    body: body,
    observe: 'response',
    credentials: 'include'
  };

  return fetch('http://localhost:8080/login', requestOptions)
    .then(gererReponse)
    .then(user => {
      // login réussi s'il y a un user dans la réponse
      if (user) {
        // store user details
        // pour garder le user loggé entre les différentes pages
        localStorage.setItem('user', JSON.stringify(user));
      }
      return user;
    });
}

function gererReponse(reponse) {
  return reponse.text().then(text => {
    const data = text && JSON.parse(text);
    if (!reponse.ok) {
      if (reponse.status === 401) {
        // auto logout if 401 response returned from api
        logout();
        location.reload(true);
      }

      const error = (data && data.message) || reponse.statusText;
      return Promise.reject(error);
    }

    return data;
  });
}

function logout() {
  // remove user from local storage to log user out
  localStorage.removeItem('user');
}
