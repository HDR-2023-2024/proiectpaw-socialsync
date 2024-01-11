#ifndef RESOURCEDETAILS_H
#define RESOURCEDETAILS_H

#include <QDialog>
#include <QJsonObject>
#include <QStandardItemModel>
#include <QAbstractItemView>
#include <QCloseEvent>

namespace Ui {
class ResourceDetails;
}

class ResourceDetails : public QDialog
{
    Q_OBJECT

public:
    explicit ResourceDetails(QWidget *parent = nullptr);
    ~ResourceDetails();

    void setDetails(QJsonValue value);

protected:
    void closeEvent(QCloseEvent *event) override {
        model->clear();
        QDialog::closeEvent(event);
    }

private:
    Ui::ResourceDetails *ui;
    QJsonValue details;
    QStandardItemModel *model;
};

#endif // RESOURCEDETAILS_H
