/********************************************************************************
** Form generated from reading UI file 'resourcedetails.ui'
**
** Created by: Qt User Interface Compiler version 6.6.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_RESOURCEDETAILS_H
#define UI_RESOURCEDETAILS_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDialog>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListView>
#include <QtWidgets/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_ResourceDetails
{
public:
    QVBoxLayout *verticalLayout_2;
    QVBoxLayout *verticalLayout;
    QLabel *label;
    QListView *listView;

    void setupUi(QDialog *ResourceDetails)
    {
        if (ResourceDetails->objectName().isEmpty())
            ResourceDetails->setObjectName("ResourceDetails");
        ResourceDetails->resize(540, 569);
        verticalLayout_2 = new QVBoxLayout(ResourceDetails);
        verticalLayout_2->setObjectName("verticalLayout_2");
        verticalLayout = new QVBoxLayout();
        verticalLayout->setObjectName("verticalLayout");
        label = new QLabel(ResourceDetails);
        label->setObjectName("label");
        QFont font;
        font.setPointSize(16);
        font.setBold(true);
        label->setFont(font);
        label->setAlignment(Qt::AlignCenter);

        verticalLayout->addWidget(label);

        listView = new QListView(ResourceDetails);
        listView->setObjectName("listView");

        verticalLayout->addWidget(listView);


        verticalLayout_2->addLayout(verticalLayout);


        retranslateUi(ResourceDetails);

        QMetaObject::connectSlotsByName(ResourceDetails);
    } // setupUi

    void retranslateUi(QDialog *ResourceDetails)
    {
        ResourceDetails->setWindowTitle(QCoreApplication::translate("ResourceDetails", "Dialog", nullptr));
        label->setText(QCoreApplication::translate("ResourceDetails", "Details", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ResourceDetails: public Ui_ResourceDetails {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_RESOURCEDETAILS_H
