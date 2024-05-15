#ifndef BIDSLIST_H
#define BIDSLIST_H

#include <QObject>
#include <QAbstractListModel>
#include <QQmlEngine>
#include "BidElement.h"

/* Istenosztályként müködik, minden funkció megvalósitásáért felel.
 * Ha többet tudnák foglalkozni akkor delegálnánk a felelőséget az adott elemnre. */
class BidsList : public QAbstractListModel
{
    Q_OBJECT

public:
    // Used for referencing data type elements as qml properties
    enum Role {
        ItemIndex = Qt::UserRole + 1,
        Subject,
        Supplier,
        Price,
        HighestBidder
        //Picture
    };
    explicit BidsList(QObject *parent = nullptr);

    // Needed to get the length from qml, as rowCount can't be used
    int count() const;

    // Inherited from QAbstractListModel
    virtual int rowCount(const QModelIndex &parent) const override;
    virtual QVariant data(const QModelIndex &index, int role) const override;
    virtual QHash<int, QByteArray> roleNames() const override;

public slots:
    // Called from the backend by event handlers.
    // Returns if the addition vas succesful
    void addBid(
        const int itemIndex,
        const QString& subject,
        const QString& supplier,
        const double price,
        const QString& highestBidder
    );
    void removeBid(int index);

    // Adding new element to the list
    void addBidElement(BidElement* element);

    // Subscribe to element with given index
    void subscribe(int index);

    // Place bid on element with given index
    void placeBid(int index, double bid, QString userId);

signals:
    // QAbstractItemModel interface

    // ads the specified element to the notification list
    // Returns if the update on the list was succesful
    void elementAdded(BidElement* element);
    void elementRemoved(int index);

    // Subscribe to the element.
    void subscribedTo(BidElement* element);

    // Bid placed succesfully on element.
    void bidPlaced(BidElement* element);

private:
    // Data element on which the model is based.s
    QList<BidElement*> bidsList;

    BidElement* findElementById(int id);
};

#endif // BIDSLIST_H
