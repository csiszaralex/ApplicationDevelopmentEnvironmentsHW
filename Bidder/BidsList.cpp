#include "BidsList.h"
#include "BidElement.h"

BidsList::BidsList(QObject *parent)
    : QAbstractListModel{parent}
{}

/* Overrides abstract function of QAbstractItemModel. Returns the number of elements in the list
 * which our model is based. Creates coresponding ItemModel qml elements. */
int BidsList::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return bidsList.length();
}

/* Overides abstract fuction of QAbstractItemModel. It serves to associate data element to qml item. */
QVariant BidsList::data(const QModelIndex &index, int role) const
{
    if(index.isValid() && index.row() >= 0 && index.row() < bidsList.length()) {
        BidElement* bid = bidsList[index.row()];
        switch((Role) role) {
            case ItemIndex: return bid->itemIndex();
            case Subject: return bid->subject();
            case Supplier: return bid->supplier();
            case Price: return bid->price();
            case HighestBidder: return bid->highestBidder();
        }
    }
    return {};
}

/* Creates aliases when using in qml code. the ByteArray type data will be
* references in qml code. The hash key is based on Role enum type*/
QHash<int, QByteArray> BidsList::roleNames() const
{
    QHash<int, QByteArray> result;
    result[ItemIndex] = "itemIndex";
    result[Subject] = "subjectName";
    result[Supplier] = "supplierName";
    result[Price] = "price";
    result[HighestBidder] = "highestBidderName";
    return result;
}

/* Function is called remotely by backend.
 * Backend was called by add button, or any other reason, The callback red all form fields, after checking the validity.
 * Backend also checked the validity of input fields, such as duplicates.
 *In case of valid fields, the backend calles the function. */
void BidsList::addBid(
    const int itemIndex,
    const QString &subject,
    const QString &supplier,
    const double price,
    const QString &highestBidder
    )
{
    //Check if backend allow insertion of element

    // Set the interval for the view element to process the request
    // Specify elements that need to be redrawn
    beginInsertRows(QModelIndex(), bidsList.length(), bidsList.length());
    BidElement* newItem = new BidElement(this);

    // Index works as an ID accross multiple lists
    newItem->setItemIndex(std::rand());

    // All the parameters are valid.
    // Validity is checked before fucntion call, by event handler.
    newItem->setSubject(subject);
    newItem->setSupplier(supplier);
    newItem->setPrice(price);
    newItem->setHighestBidder(highestBidder);

    // Add new element to the list
    bidsList << newItem;
    qDebug() << "Added bid item with --index:" << newItem->itemIndex()
             << " with --title: " << newItem->subject()
             << " number of total records are: " << bidsList.length();
    endInsertRows();
    emit elementAdded(newItem);
}

/* Function is called remotely by backend
 * Backend was called by the remove button on the BidViewElement,
 * or any other reason. Although validity was checked we also check redundantly. */
void BidsList::removeBid(int index)
{
    // Find the element with the coresponding id. If doesnt exist nullptr
    BidElement* bid = findElementById(index);

    if (bid != nullptr) {
        int position = bidsList.indexOf(bid);
        beginRemoveRows(QModelIndex(), position, position);
        // Check if the backend allow the deletion of element

        // Remove from the list and log
        bidsList.removeAll(bid);

        qDebug() << "Removed bid item with --index:" << bid->itemIndex()
                 << " with --title: " << bid->subject()
                 << " number of total records are: " << bidsList.length();

        // Before deleting element deletion server request
        // SERVER REST API REQUEST 

        // Asyncronous call of delete, preventing memory leaking
        bid->deleteLater();
        endRemoveRows();

        // Signal that the item has been removed from the list
        emit elementRemoved(index);
    } else {
        // If the server or the index validity drops the procedure
        qDebug() << "Item removel with index: " << index << " was unsuccesful";
    }
}

void BidsList::addBidElement(BidElement *element)
{   
    /* Redundant check if the element exist in the list.
     * Backend also checks the existance of sad element. */
    BidElement* check = findElementById(element->itemIndex());

    if(check == nullptr) {
        beginInsertRows(QModelIndex(), bidsList.length(), bidsList.length());

        // Insert into list
        bidsList.append(element);

        // Loging client side model logic before server request
        qDebug() << "Added bid item with --index:" << element->itemIndex()
                 << " with --title: " << element->subject()
                 << " number of total records are: " << bidsList.length();
        endInsertRows();

        // REST API SERVER REQUEST
    } else {
        qDebug() << "Element with --index:" << element->itemIndex()
                 << " and --title: " << element->subject()
                 << " already exist in the list ";
    }
}

BidElement *BidsList::findElementById(int id)
{
    // find element with the specified id
    QList<BidElement*>::iterator i;
    for(i = bidsList.begin(); i != bidsList.end(); ++i) {
        BidElement* bid = *i;
        if(bid->itemIndex() == id)
            return bid;
    }
    qDebug() << "Element with --index: " << id << " not found";
    return nullptr;
}

// Needed in qml code, as functions can be accesed
int BidsList::count() const { return bidsList.length(); }

void BidsList::subscribe(int index)
{
    BidElement* element = findElementById(index);
    if(element != nullptr) {
        emit subscribedTo(element);
    }
}

void BidsList::placeBid(int index, double bid, QString userId)
{
    BidElement* element = findElementById(index);

    if(element != nullptr) {
        if(bid > element->price()) {
            element->setPrice(bid);
            element->setHighestBidder(userId);

            qDebug() << "Bid placed succesfully on item --index: " << element->itemIndex()
                     << " -> new highest bid amount is: " << element->price()
                     << " from --user: " << element->highestBidder();
            emit bidPlaced(element);
        } else {
            qDebug() << "Bid amount on item --index: " << element->itemIndex()
                     << "is to low: --amount: " << bid
                     << " current bid: " << element->price();
        }
    }
}
