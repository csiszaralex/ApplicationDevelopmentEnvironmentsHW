#include <QGuiApplication>
#include <QIcon>
#include <QQmlApplicationEngine>

#include "User.h"
#include "BidsList.h"

void initialize(BidsList* list);

int main(int argc, char *argv[])
{

#if QT_VERSION < QT_VERSION_CHECK(6, 0, 0)
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
#endif

    QGuiApplication app(argc, argv);
    QQmlApplicationEngine engine;

    // Register user for qml code.
    // Needed for referencing the currently logged in user name
    User* user = new User(&app);
    user->setName("vargadavid230");
    qmlRegisterSingletonInstance("com.User",1,0, "User", user);


    // Register all the data models for the qml code
    BidsList* exploreList = new BidsList(&app);
    qmlRegisterSingletonInstance("com.model.ExploreList",1,0, "ExploreList", exploreList);

    BidsList* watchList = new BidsList(&app);
    qmlRegisterSingletonInstance("com.model.WatchList",1,0, "WatchList", watchList);

    BidsList* owngoodsList = new BidsList(&app);
    qmlRegisterSingletonInstance("com.model.OwnGoods",1,0, "OwnGoods", owngoodsList);

    BidsList* bidsList = new BidsList(&app);
    qmlRegisterSingletonInstance("com.model.BidsList",1,0, "BidsList", bidsList);


    // Ha új elemet hozunk létre akkor a mindekinek megjelenjen
    QObject::connect(owngoodsList, &BidsList::elementAdded,
                     exploreList, &BidsList::addBidElement);

    // Ha kitörlöljük a saját hirdetésünket akkor semmisüljon meg publikusan is
    QObject::connect(owngoodsList, &BidsList::elementRemoved,
                    exploreList, &BidsList::removeBid);

    // Ha egy hirdetés megsemmisült, nem kaphatunk rolla értesítést.
    QObject::connect(exploreList, &BidsList::elementRemoved,
                     watchList, &BidsList::removeBid);

    // Ha egy hirdetés megsemmisült akkor a licitünk is eltűnik.
    QObject::connect(exploreList, &BidsList::elementRemoved,
                     bidsList, &BidsList::removeBid);

    // Ha feliratkozunk egy licitre akkor jelenjen meg a watchlistbenn
    QObject::connect(exploreList, &BidsList::subscribedTo,
                     watchList, &BidsList::addBidElement);

    // Ha licitalunk a főoldalon akkor a tét jelenjen meg a licit ablakban
    QObject::connect(exploreList, &BidsList::bidPlaced,
                     bidsList, &BidsList::addBidElement);

    // Ha licitálunk egy watchlistben lévő tétre akkor is jelenjen meg a licitbe
    QObject::connect(watchList, &BidsList::bidPlaced,
                     bidsList, &BidsList::addBidElement);

    // Inicializaljuk az adatokat:
    initialize(exploreList);

    const QUrl url(QStringLiteral("qrc:/Bidder/qml/Main.qml"));
    QObject::connect(
        &engine,
        &QQmlApplicationEngine::objectCreationFailed,
        &app,
        []() { QCoreApplication::exit(-1); },
        Qt::QueuedConnection);
    engine.load(url);

    return app.exec();
}

void initialize(BidsList* list) {
    list->addBid(0,"Queen Size 4 Piece Sheet Set - Comfy Breathable & Cool","smukferi69",25.49,"davidvarga230");
    list->addBid(1,"Beats Studio Buds with AppleCare+ for Headphones - Black", "csiszarAlex23",99.00, "davidvarga230");
    list->addBid(2,"Amazon Essentials Men's 9 Quick-Dry Swim Trunk", "csiszarAlex23", 5.60, "davidvarga230");
    list->addBid(3,"Samusung Galaxy Tab A9+ Tablet 11, 64GB Android Tablet", "davidvarga230", 169.99, "smukferi69");
    list->addBid(4,"Amazon Essentials Women's Cotton Bikini Brief", "davidvarga230", 8.9, "smukferi69");
    list->addBid(5,"DEWALT 20V MAX Cordless Drill and Impact Drive", "davidvarga230", 129.99, "csiszarAlex23");
    list->addBid(6,"Coway Airmega AP-1512HH(W) True HEPA Purifier with Air Quality Monitoring", "csiszarAlex23", 131.64, "smukferi69");
    list->addBid(7,"Blink Outdoor 4 (4th Gen) + Battery Extension Pack", "csiszarAlex", 134.99, "davidvarga230");
    list->addBid(8,"Amazon Essentials Men's Slim-Fit 9 Shors", "smukferi69", 7.4, "csiszarAlex230");
    list->addBid(9,"Amazon Essentials Men's 10 Lightweight Ripstop Stretch Cargo Short", "csiszarAlex23", 7.4, "smukferi69");
    list->addBid(10,"Amazon Essentials Men's Drawstring Walk Short", "csiszarAlex230", 7.4, "davidvarga230");
}

