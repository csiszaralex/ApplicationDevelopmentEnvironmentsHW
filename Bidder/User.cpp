#include "User.h"

User::User(QObject *parent)
    : QObject{parent}
{
    /* If the name of the user changes all the items owner name should change as well */
}

User::User(User& user) {
    setName(user.name());
}
QString User::name() const
{
    return m_name;
}

void User::setName(const QString &newName)
{
    if (m_name != newName) {
        m_name = newName;
        //emit nameChanged();
    }
}
