import * as b from 'bobril';

import * as homePage from './pages/HomePage';
import * as wrapperPage from './pages/WrapperPage';
import * as trendyPage from './pages/TrendyPage';
import * as loginPage from './pages/LoginPage';
import * as userPage from './pages/UserPage';
import *as findPage from './pages/FindPage'
import * as galleryPage from './pages/GalleryPage';
import * as uWrapper from './pages/UWrapper'


export const img = b.asset("assets/img.jpg");
export const img2 = b.asset("assets/img2.jpg");

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

b.routes([
        b.route({handler: wrapperPage.create}, [
                b.route({name: 'trendy', url: '/trendy', handler: trendyPage.create}),
                b.route({name: 'gallery', url: '/gallery', handler: galleryPage.create}),
                b.route({name: 'find', url: '/find', handler: findPage.create}),
                b.route({name: 'login', url: '/login', handler: loginPage.create}),
                b.routeDefault({handler: homePage.create})
            ]
        ),
        b.route({handler: uWrapper.create}, [
                b.route({name: 'Uhome', url: '/user', handler: homePage.create}),
                b.route({name: 'Utrendy', url: '/user/trendy', handler: trendyPage.create}),
                b.route({name: 'Ugallery', url: '/user/gallery', handler: galleryPage.create}),
                b.route({name: 'Ufind', url: '/user/find', handler: findPage.create}),
                b.route({name: 'Uoption', url: '/user/option', handler: userPage.create}),
                b.routeDefault({handler: homePage.create}),
            ]
        )
    ]
);