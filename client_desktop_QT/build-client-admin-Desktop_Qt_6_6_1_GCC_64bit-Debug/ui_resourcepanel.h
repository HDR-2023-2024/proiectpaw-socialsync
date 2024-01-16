/********************************************************************************
** Form generated from reading UI file 'resourcepanel.ui'
**
** Created by: Qt User Interface Compiler version 6.6.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_RESOURCEPANEL_H
#define UI_RESOURCEPANEL_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListView>
#include <QtWidgets/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_ResourcePanel
{
public:
    QVBoxLayout *verticalLayout_2;
    QVBoxLayout *verticalLayout;
    QLabel *panelTitle;
    QListView *listView;

    void setupUi(QDialog *ResourcePanel)
    {
        if (ResourcePanel->objectName().isEmpty())
            ResourcePanel->setObjectName("ResourcePanel");
        ResourcePanel->resize(754, 300);
        verticalLayout_2 = new QVBoxLayout(ResourcePanel);
        verticalLayout_2->setObjectName("verticalLayout_2");
        verticalLayout = new QVBoxLayout();
        verticalLayout->setObjectName("verticalLayout");
        panelTitle = new QLabel(ResourcePanel);
        panelTitle->setObjectName("panelTitle");
        QFont font;
        font.setPointSize(16);
        panelTitle->setFont(font);
        panelTitle->setAlignment(Qt::AlignCenter);

        verticalLayout->addWidget(panelTitle);

        listView = new QListView(ResourcePanel);
        listView->setObjectName("listView");

        verticalLayout->addWidget(listView);


        verticalLayout_2->addLayout(verticalLayout);


        retranslateUi(ResourcePanel);

        QMetaObject::connectSlotsByName(ResourcePanel);
    } // setupUi

    void retranslateUi(QDialog *ResourcePanel)
    {
        ResourcePanel->setWindowTitle(QCoreApplication::translate("ResourcePanel", "Resource Panel", nullptr));
        panelTitle->setText(QCoreApplication::translate("ResourcePanel", "TextLabel", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ResourcePanel: public Ui_ResourcePanel {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_RESOURCEPANEL_H
