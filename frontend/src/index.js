import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import { unregister } from './serviceWorker';

import HomePage from './pages/home/HomePage';
import NotFoundPage from './pages/404/NotFoundPage';

import './index.css';

ReactDOM.render(
    <BrowserRouter>
        <Switch>
            <Route exact path="/" component={HomePage}/>
            <Route component={NotFoundPage}/>
        </Switch>
    </BrowserRouter>,
    document.getElementById('root')
);

unregister();
