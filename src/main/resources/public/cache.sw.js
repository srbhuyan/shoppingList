var CACHE_VERSION =  2;
var CACHE_NAME = 'shopping-list-cache-v' + CACHE_VERSION;

var urlsToCache = [
    '/index.html',
    '/sw.js',
    '/manifest.json',
    '/style/fonts/roboto_100_italic_latin-ext.woff2',
    '/style/fonts/roboto_300_italic_latin-ext.woff2',
    '/style/fonts/roboto_400_italic_latin-ext.woff2',
    '/style/fonts/roboto_500_italic_latin-ext.woff2',
    '/style/fonts/roboto_700_italic_latin-ext.woff2',
    '/style/fonts/roboto_900_italic_latin-ext.woff2',
    '/style/fonts/roboto_100_italic_latin.woff2',
    '/style/fonts/roboto_300_italic_latin.woff2',
    '/style/fonts/roboto_400_italic_latin.woff2',
    '/style/fonts/roboto_500_italic_latin.woff2',
    '/style/fonts/roboto_700_italic_latin.woff2',
    '/style/fonts/roboto_900_italic_latin.woff2',
    '/style/fonts/roboto_100_normal_latin-ext.woff2',
    '/style/fonts/roboto_300_normal_latin-ext.woff2',
    '/style/fonts/roboto_400_normal_latin-ext.woff2',
    '/style/fonts/roboto_500_normal_latin-ext.woff2',
    '/style/fonts/roboto_700_normal_latin-ext.woff2',
    '/style/fonts/roboto_900_normal_latin-ext.woff2',
    '/style/fonts/roboto_100_normal_latin.woff2',
    '/style/fonts/roboto_300_normal_latin.woff2',
    '/style/fonts/roboto_400_normal_latin.woff2',
    '/style/fonts/roboto_500_normal_latin.woff2',
    '/style/fonts/roboto_700_normal_latin.woff2',
    '/style/fonts/roboto_900_normal_latin.woff2',
    '/templates/authentication/login/login.html',
    '/templates/navigation/navigation.html',
    '/templates/navigation/navMenu/navMenu.html',
    '/templates/navigation/leftSideNav/leftSideNav.html',
    '/templates/user/delete/userDelete.html',
    '/templates/user/edit/userData.html',
    '/templates/lists/list.html',
    '/templates/lists/lists.html',
    '/templates/lists/view/view.html',
    '/templates/lists/view/emptyList.html',
    '/templates/lists/new/newList.html',
    '/templates/lists/edit/editList.html',
    '/templates/directives/loadingCycle.html',
    '/templates/authentication/register/register.html',
    '/templates/authentication/register/confirmation/confirmationNotification.html',
    '/templates/authentication/register/confirmation/confirmation.html',
    '/templates/item/new/newItem.html',
    '/templates/item/item.html',
    '/templates/article/article.html',
    '/templates/article/dictionary/dictionary.html',
    '/templates/article/dictionary/emptyDictionary.html',
    '/style/css/app-compact.css',
    '/js/ShoppingList.min.js',
    '/img/icons/action/ic_add_shopping_cart_24px.svg',
    '/img/icons/action/ic_lock_outline_24px.svg',
    '/img/icons/action/ic_shopping_cart_24px.svg',
    '/img/icons/action/ic_delete_24px.svg',
    '/img/icons/action/ic_settings_24px.svg',
    '/img/icons/action/ic_list_24px.svg',
    '/img/icons/action/ic_search_24px.svg',
    '/img/icons/action/ic_chrome_reader_mode_24px.svg',
    '/img/icons/communication/ic_email_24px.svg',
    '/img/icons/communication/ic_vpn_key_24px.svg',
    '/img/icons/communication/ic_clear_all_24px.svg',
    '/img/icons/content/ic_remove_circle_24px.svg',
    '/img/icons/content/ic_save_24px.svg',
    '/img/icons/hardware/ic_keyboard_arrow_left_24px.svg',
    '/img/icons/navigation/ic_menu_24px.svg',
    '/img/icons/navigation/ic_more_vert_24px.svg',
    '/img/icons/navigation/ic_refresh_24px.svg',
    '/img/icons/navigation/ic_close_24px.svg',
    '/img/icons/navigation/ic_arrow_back_24px.svg',
    '/img/icons/notification/ic_sync_24px.svg',
    '/img/icons/notification/ic_sync_disabled_24px.svg',
    '/img/icons/notification/ic_sync_problem_24px.svg',
    '/img/icons/social/ic_mood_bad_48px.svg',
    '/img/icons/social/ic_person_24px.svg',
    '/img/icons/social/ic_person_add_24px.svg',
    '/img/icons/Toggle/ic_check_box_24px.svg',
    '/img/icons/Toggle/ic_check_box_outline_blank_24px.svg'
];

self.addEventListener('install', (event) => {
    event.waitUntil(
        caches.open(CACHE_NAME)
            .then(function (cache) {
                return cache.addAll(urlsToCache);
            })
    );
});

self.addEventListener('activate', (event) => {
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return Promise.all(
                cacheNames.map(cacheName => {
                    if(cacheName !== CACHE_NAME) {
                        return caches.delete(cacheName);
                    }
                })
            );
        })
    );
});

self.addEventListener('fetch', function(event) {
    event.respondWith(
        caches.match(event.request)
            .then(function(response) {
                // Cache hit - return response
                if (response) {
                    return response;
                }
                return fetch(event.request);
            })
    );
});