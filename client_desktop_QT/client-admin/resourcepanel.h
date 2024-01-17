#ifndef RESOURCEPANEL_H
#define RESOURCEPANEL_H

#include <QDialog>
#include <QJsonDocument>
#include <QJsonObject>
#include <QStandardItemModel>
#include <QListView>
#include <resourcedetails.h>

namespace Ui {
class ResourcePanel;
}

class ResourcePanel : public QDialog
{
    Q_OBJECT

public:
    explicit ResourcePanel(QWidget *parent = nullptr);
    ~ResourcePanel();
    void setTitle(QString title);
    void setContent(QString content);
    void setUrl(QString url);
    void setToken(QString token);

private:
    Ui::ResourcePanel *ui;
    QJsonObject content;
    QStandardItemModel *model;
    ResourceDetails *resourceDetails;
    QString resourceName;
    QString url;
    QString token;
    QNetworkAccessManager *manager;

protected:
    void closeEvent(QCloseEvent *event) override {
        model->clear();
        QDialog::closeEvent(event);
    }
private slots:
    void on_listView_doubleClicked(const QModelIndex &index);
};

#endif // RESOURCEPANEL_H
