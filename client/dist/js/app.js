axios.defaults.withCredentials = true;

const config = {
    headers: {
        'Content-Type': 'application/json'
    }
};

let loginRequiredRedirect = (page) => {
    app.loginSuccessPage = page;
    window.location.href = 'login.html';
};

let loginSuccessRedirect = () => {
    if (app.loginSuccessPage != '') {
        window.location.href = app.loginSuccessPage;
        app.loginSuccessPage = '';
    } else {
        window.location.href = 'headlines.html';
    }
};

let serverResponseError = () => {
    window.location.href = 'error.html';
};

let loadUserProfile = () => {
    axios.get('http://localhost:8080/api/profile')
        .then((resp) => {
            app.userprofile = resp.data;
        })
        .catch(() => {
            loginRequiredRedirect("profile.html");
        })
};

let loadSources = () => {
    axios.get('http://localhost:8080/api/source/all/')
        .then((resp) => {
            app.sources = resp.data;
            app.navdata.currentSource = null;
        })
        .catch(() => {
            loginRequiredRedirect("headlines.html");
        })
};

let loadHeadlines = (pageNumber) => {
    axios.get('http://localhost:8080/api/headline/page/' + pageNumber)
        .then((resp) => {
            app.headlines = resp.data.content;
            app.navdata = {
                lastPage: resp.data.last,
                totalPages: resp.data.totalPages,
                totalHeadlines: resp.data.totalElements,
                showing: resp.data.size,
                page: resp.data.number,
                firstPage: resp.data.first,
                currentSource: null
            };
        })
        .catch(() => {
            loginRequiredRedirect("headlines.html");
        })
};

let loadHeadlinesBySource = (source, page) => {
    axios.get('http://localhost:8080/api/headline/' + source + '/page/' + page)
        .then((resp) => {
            app.headlines = resp.data.content;
            app.navdata = {
                lastPage: resp.data.last,
                totalPages: resp.data.totalPages,
                totalHeadlines: resp.data.totalElements,
                showing: resp.data.size,
                page: resp.data.number,
                firstPage: resp.data.first,
                currentSource: source
            };
        })
        .catch(() => {
            loginRequiredRedirect("headlines.html");
        })
};

let updateHeadlinePropertyCount = (endpoint, id, prop) => {
    axios.get(endpoint)
        .then((resp) => {
            if (resp.status === 200) {
                app.headlines.map((obj) => {
                    if (obj.id === id) {
                        obj[prop]++;
                    }
                })
            }
        })
        .catch(() => {
            loginRequiredRedirect("headlines.html");
        })
};

let logout = () => {
    axios.get('http://localhost:8080/api/logout')
        .then((resp) => {
            window.location.href = 'login.html';
        })
        .catch(() => {
            serverResponseError();
        })
};

Vue.component('navigation-bar', {
    props: ['navdata', 'sources'],
    template: '<nav class="navbar navbar-expand-lg navbar-light bg-light">' +
    '<a class="navbar-brand"><span v-if="navdata.currentSource">{{navdata.currentSource}} </span>Headlines: {{navdata.totalHeadlines}}</a><button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span>' +
    '</button>' +
    '<div class="collapse navbar-collapse" id="navbarSupportedContent">' +
    '<ul class="navbar-nav mr-auto">' +
    '<li class="nav-item">' +
    '<a class="nav-link" href="headlines.html">Headlines</a>' +
    '</li>' +
    '<li class="nav-item dropdown">' +
    '<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sources</a>' +
    '<div class="dropdown-menu" aria-labelledby="navbarDropdown">' +
    '<a class="dropdown-item" v-for="source in sources" v-on:click="viewAllByThisNewsSource(source.name,0)">{{source.name}}</a>' +
    '</div>' +
    '</li>' +
    '<li class="nav-item">' +
    '<a class="nav-link" href="profile.html">Profile</a>' +
    '</li>' +
    '<li class="nav-item">' +
    '<a class="nav-link" v-on:click="logout">Logout</a>' +
    '</li>' +
    '</ul>' +
    '</div>' +
    '</nav>',
    methods: {
        viewAll: () => {
            loadHeadlines(0);
        },
        viewAllByThisNewsSource: (source, page) => {
            loadHeadlinesBySource(source, page);
            app.navdata.currentSource = source;
        },
        logout: () => {
            logout();
        }
    },
    beforeMount: () => {
        loadHeadlines(0);
        loadSources();
    }
});

Vue.component('headline-nav', {
    props: ['navdata'],
    template: '<div class="row">' +
    '<div class="col-md-2">' +
    '<p><a v-if="!navdata.firstPage" v-on:click="previousHeadlines(navdata.page, navdata.firstPage)">Previous</a></p>' +
    '</div>' +
    '<div class="col-md-8">' +
    '<p align="center"><em>Page {{navdata.page + 1}} of {{navdata.totalPages}}</em></p>' +
    '</div>' +
    '<div class="col-md-2">' +
    '<p align="right"><a v-if="!navdata.lastPage" v-on:click="nextHeadlines(navdata.page, navdata.lastPage)">Next</a></p>' +
    '</div>' +
    '</div>',
    methods: {
        nextHeadlines: (currentPage, allow) => {
            if (!allow) {
                let page = currentPage + 1;
                if (app.navdata.currentSource) {
                    loadHeadlinesBySource(app.navdata.currentSource, page)
                } else {
                    loadHeadlines(page);
                }
            }
        },
        previousHeadlines: (currentPage, allow) => {
            if (!allow) {
                let page = currentPage - 1;
                if (app.navdata.currentSource) {
                    loadHeadlinesBySource(app.navdata.currentSource, page)
                } else {
                    loadHeadlines(page);
                }
            }
        }
    }
});

Vue.component('headline', {
    props: ['headlines'],
    template: '<div class="row">' +
    '<div class="col-md-12" v-for="headline in headlines" v-bind:key="headline.id">' +
    '<h5>{{headline.headline}}</h5>' +
    '<p><strong>{{headline.source}}</strong><br/>' +
    '<span class="badge badge-warning">{{headline.views}}</span> <a :href="headline.link" target="_blank" v-on:click="view(headline.id)">View Article</a> ' +
    '| <span class="badge badge-secondary">{{headline.likes}}</span> <a v-on:click="like(headline.id)">Like</a> | Scraped: {{ new Date(headline.date) }}</p>' +
    '<hr/>' +
    '</div>' +
    '</div>',
    methods: {
        like: (id) => {
            updateHeadlinePropertyCount('http://localhost:8080/api/headline/like/' + id, id, 'likes');
        },
        view: (id) => {
            updateHeadlinePropertyCount('http://localhost:8080/api/headline/view/' + id, id, 'views');
        }
    }
});

Vue.component('user', {
    props: ['user'],
    template: '<div class="row">' +
    '<div class="col-md-12">' +
    '<h4>{{user.firstname}} {{user.lastname}} ({{user.username}})</h4>' +
    '<p>{{user.email}}<br/>' +
    'Area Code {{user.zip}}</p>' +
    '</div>' +
    '<div class="col-md-6">' +
    '<h5>Recently liked headlines</h5>' +
    '<p v-for="headline in user.likedHeadlines">{{headline}}</p>' +
    '</div>' +
    '<div class="col-md-6">' +
    '<h5>Recently viewed headlines</h5>' +
    '<p v-for="headline in user.viewedHeadlines">{{headline}}</p>' +
    '</div>' +
    '</div>' +
    '</div>',
    methods: {},
    beforeMount: () => {
        loadUserProfile();
    }
});

let app = new Vue({
    el: "#app",
    methods: {
        login: (a) => {
            axios.post('//localhost:8080/api/login', app.auth, config
            )
                .then(() => {
                    loginSuccessRedirect();
                })
                .catch(() => {
                    loginRequiredRedirect();
                })
        },
        signup: () => {
            console.log(app.auth);
            axios.post('//localhost:8080/api/signup', app.auth, config
            )
                .then(() => {
                    loginSuccessRedirect();
                })
                .catch(() => {
                    loginRequiredRedirect();
                })
        }
    },
    data: {
        sources: [],
        headlines: [],
        currentPage: '',
        navdata: {},
        auth: {},
        userprofile: {},
        loginSuccessPage: ''
    }
});