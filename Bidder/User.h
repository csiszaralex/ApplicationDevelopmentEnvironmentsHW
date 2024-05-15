#ifndef USER_H
#define USER_H

#include <QObject>
#include <QDebug>
#include "BidElement.h"

class User : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString name READ name WRITE setName NOTIFY nameChanged)

public:
    explicit User(QObject *parent = nullptr);
    explicit User(User& user);

    QString name() const;
    void setName(const QString &newName);

signals:
    void nameChanged();

private:
    QString m_name;
};

#endif // USER_H
